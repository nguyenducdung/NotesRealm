package rikkeisoft.nguyenducdung.com.notesrealm.db;

import java.util.Date;

import io.realm.Realm;
import io.realm.RealmResults;
import rikkeisoft.nguyenducdung.com.notesrealm.model.Note;

public class RealmController {
    private Realm realm;

    public RealmController() {
        realm = Realm.getDefaultInstance();
    }

    public RealmResults<Note> getAllNotes(){
        realm.beginTransaction();
        RealmResults<Note> rs = realm.where(Note.class).findAll();
        realm.commitTransaction();
        return rs;
    }

    public int insertNote(String content_note) {
        Note note = realm.createObject(Note.class);
        Date date = new Date();
        String timeStamp = String.valueOf(date.getTime());
        realm.beginTransaction();
        note.setId(1);
        note.setNote(content_note);
        note.setTimestamp(timeStamp);
        realm.commitTransaction();
    }

    public void updateNote(Note note) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                note = realm.createObject(Note.class);
                realm.copyToRealmOrUpdate(note);
                realm.commitTransaction();
            }
        });
    }

    public void deleteNote(Note note) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                note = realm.where(Note.class).findFirst();;
                note.removeFromRealm();
                realm.commitTransaction();
            }
        });
    }


    public Note getNote(long id) {
        return realm.where(Note.class).equalTo("id", id).findFirst();
    }

    public long getNotesCount() {
        return realm.where(Note.class).count();
    }
}
