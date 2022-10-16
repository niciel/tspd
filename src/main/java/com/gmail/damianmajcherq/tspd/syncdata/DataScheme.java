package com.gmail.damianmajcherq.tspd.syncdata;

import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/***
 * should be used with VERY HIGH risk of data corruption if used in wrong way
 * will works if
 * - array is stored in heap as one array with continuous elements
 * - unsafe volatile works as i suspect: any change or get is directly announced to main memory
 * compered to one dimensional array of object to store primitive datatypes (boxed), array size 10M
 *                  should be safe if                                       should be safe if
 *                  proper use                                              proper use
 *                  DataScheme [ms]volatile     DataScheme [ms]             Object[] [ms]
 * initialization   56                          213 (should clear heap?)    8
 * set              208                         28                          227
 * get              60                          23                          26
 */
public class DataScheme {

    private static Unsafe unsafe;
    private static int byteArrayOffset;
    // bytes in cache
    private static int cacheSize = 8;



    // should be multiple of 64 suspect that array object is cache aligned
    private int byteSize;
    private int[] reorder;
    // depends on previous record
    private int[] dataOffset;
    private byte[] structureData;

    private int rows;
    private int elementsInRow;

    static {
        try {
            Field f = Unsafe.class.getDeclaredField("theUnsafe");
            f.setAccessible(true);
            unsafe = (Unsafe) f.get(null);
            byteArrayOffset = unsafe.arrayBaseOffset(byte[].class);
        } catch (Exception ex) {
            ex.printStackTrace();
            unsafe = null;
        }

    }

    public DataScheme(int rowSize,int rowsCount, int[] reorder, int[] dataOffset) {
        this.rows = rowsCount;
        this.elementsInRow = dataOffset.length;
        this.byteSize = rowSize;
        this.reorder = reorder;
        this.dataOffset = dataOffset;
        this.structureData = new byte[byteSize*rowsCount];
    }

    public int getInt(int row, int order) {
        return unsafe.getInt(structureData,getOffset(row,order));
    }

    public void setInt(int row, int order,int value) {
        if (!this.lazyValidation(row,order))
            return;
        unsafe.putInt(structureData,getOffset(row,order), value);
    }

    public byte getByte(int row,int order){
        return unsafe.getByte(structureData,getOffset(row,order));
    }

    public void setByte(int row,int order,byte value){
        if (!this.lazyValidation(row,order))
            return;
        unsafe.putByte(structureData,getOffset(row,order), value);
    }


    //
    public int getIntVolatile(int row, int order) {
        return unsafe.getIntVolatile(structureData,getOffset(row,order));
    }

    public void setIntVolatile(int row, int order,int value) {
        unsafe.putIntVolatile(structureData,getOffset(row,order), value);
    }

    public byte getByteVolatile(int row,int order){
        return unsafe.getByteVolatile(structureData,getOffset(row,order));
    }

    public void setByteVolatile(int row,int order,byte value){
        unsafe.putByteVolatile(structureData,getOffset(row,order), value);
    }

    protected int getOffset(int row, int position) {
        return byteArrayOffset + byteSize*row+dataOffset[this.reorder[position]];
    }

    protected boolean lazyValidation(int row,int pos) {
        if (row < 0 || pos < 0)
            return false;
        if (row >= this.rows || pos >= elementsInRow)
            return false;
        return true;
    }
    public int getRowBytes() {
        return this.byteSize;
    }
    public static class Builder{

        // for 64 bytes alignment
        private static int padding = 8;
        public List<DataType> type = new ArrayList();

        public Builder append(DataType type) {
            this.type.add(type);
            return this;
        }

        public DataScheme build(int rows) {
            List<DataType> d = type.stream().sorted( (a,b)-> {
                if (a.size < b.size)
                    return 1;
                else if (a.size == b.size)
                    return 0;
                return -1;
            }).toList();

            int alignment = 0;
            DataType[] copy = new DataType[type.size()];
            type.toArray(copy);
            int[] reorder = new int[type.size()];
            int[] offset = new int[type.size()];
            int add  = 0;
            for (int i = 0 ;i < type.size();i++){

                DataType type = d.get(i);
                for (int j = 0 ;j < copy.length;j++){
                    if (copy[j] == null)
                        continue;
                    if (copy[j]==type){
                        copy[j] = null;
                        reorder[j] = i;
                        break;
                    }
                }
                //no reason for checking alignment
                offset[i] = add;
                add += type.size;
            }

            // could use bitwise modulo but not yet TODO
            int mod = add%DataScheme.cacheSize;
            if (mod != 0)
                add+= DataScheme.cacheSize - mod;
            return new DataScheme(add,rows,reorder,offset);
        }

        public enum DataType {
            INT(4),
            BYTE(1);
            // needs to be class that be able to hold fixed length string
            int size;

            DataType(int size) {
                this.size = size;
            }
        }
    }

}
