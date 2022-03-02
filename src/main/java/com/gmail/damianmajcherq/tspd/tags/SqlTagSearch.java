package com.gmail.damianmajcherq.tspd.tags;

import com.gmail.damianmajcherq.tspd.SqLiteManagement;
import com.gmail.damianmajcherq.tspd.SqlModule;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SqlTagSearch extends SqlModule {


    public SqlTagSearch(SqLiteManagement sql) {
        super(sql);
    }


    public void removeTagGroup(Connection con , int tagGroup) {
        try (PreparedStatement ps = con.prepareStatement("DELETE FROM tag_ins WHERE (id = ?)")){
            ps.setInt(1,tagGroup);
            ps.execute();
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }


    public List<String> getAllTags(){
        ArrayList list = new ArrayList();
        String query = "SELECT * FROM tag";
        try (Connection con = this.sql.getConnection();
             PreparedStatement statement = con.prepareStatement(query)) {
            try (ResultSet res = statement.executeQuery()) {
                readTags(list,res);
            }catch (SQLException e){e.printStackTrace();}
        }catch (SQLException e){e.printStackTrace();}
        return list;
    }

    public List<String> SearchTags(String prefix) {
        ArrayList list = new ArrayList();

        String query = "SELECT * FROM tag WHERE tag LIKE +'"+prefix+"%'";
        try (Connection con = this.sql.getConnection();
             PreparedStatement statement = con.prepareStatement(query)) {
            try (ResultSet res = statement.executeQuery()) {
                readTags(list,res);
            }
            catch (SQLException e){e.printStackTrace();}
        }
        catch (SQLException e){e.printStackTrace();}
        return list;
    }

    private void readTags(List<String> list , ResultSet res) throws SQLException {
        while (res.next()) {
            list.add(res.getString(2));
        }
    }


    public int registerNewTagGroup(Connection con) {
        try (Statement st = con.createStatement()) {
            st.execute("INSERT INTO tag_ins VALUES (NULL)");
            try (   PreparedStatement ps = con.prepareStatement("sqlite3_last_insert_rowid()");
                    ResultSet set = ps.executeQuery();
            ) {
                set.next();
                return set.getInt(0);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public boolean addTag(String tag){
        try (Connection con = this.sql.getConnection();
            PreparedStatement st = con.prepareStatement("INSERT INTO tag (id,tag) VALUES (NULL,?)");){
            st.setString(1,tag);
            st.execute();
            return true;
        }catch (SQLException e){e.printStackTrace();}
        return false;
    }



    public void init() {
        try {
            Connection con = this.sql.getConnection();
            Statement st = con.createStatement();
            String statement;
            statement = "CREATE TABLE IF NOT EXISTS tag (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "tag char(64) NOT NULL," +
                    "UNIQUE(tag)" +
                    ");";
            st.execute(statement);

            statement = "CREATE TABLE IF NOT EXISTS tag_ins (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT" +
                    ");";
            st.execute(statement);

            statement = "CREATE TABLE IF NOT EXISTS tags (" +
                    "id int NOT NULL," +
                    "tag int NOT NULL," +
                    "FOREIGN KEY (id) REFERENCES tag_ins(id) ON DELETE CASCADE, " +
                    "FOREIGN KEY (tag) REFERENCES tag(id) ON DELETE CASCADE" +
                    ");";
            st.execute(statement);



        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


}
