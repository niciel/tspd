initial commit


TODO
- some basic modularity (like searching modules, dependency)
- ~~tags (sqlite, editor, some view) <~~ tags ? ehm usles
- employ module < base module ?
- competency matrix (or some kind of this think) < i dont know how yet :D


public interface ITSPDModule {

    void preStart(MainModule main);
    void onStart(MainModule main);
    void initFrames(MainModule main);
}


