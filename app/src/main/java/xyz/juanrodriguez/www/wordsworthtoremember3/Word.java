package xyz.juanrodriguez.www.wordsworthtoremember3;

public class Word {
    private int _id;
    private String _word;
    private String _definition;

    public Word(){

    }

    public Word(int id, String word, String definition){
        this._id = id;
        this._word = word;
        this._definition = definition;
    }

    public Word(String word, String definition){
        this._word = word;
        this._definition = definition;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String get_word() {
        return _word;
    }

    public void set_word(String _word) {
        this._word = _word;
    }

    public String get_definition() {
        return _definition;
    }

    public void set_definition(String _definition) {
        this._definition = _definition;
    }
}