package com.callebdev.spring_firebase.services;

import com.callebdev.spring_firebase.models.Student;
import com.callebdev.spring_firebase.utils.Constants;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public class StudentService {

    public String saveStudent(Student student) throws InterruptedException, ExecutionException {

        Firestore firestore = FirestoreClient.getFirestore();

        ApiFuture<WriteResult> collectionsApiFuture = firestore
                .collection("students")
                .document(student.getEmail()).set(student);

        return collectionsApiFuture.get().getUpdateTime().toString();
    }

    public Student getStudentById(String id) throws InterruptedException, ExecutionException {

        Firestore firestore = FirestoreClient.getFirestore();
        DocumentReference documentReference = firestore
                .collection(Constants.COLLECTION_STUDENT)
                .document(id);

        ApiFuture<DocumentSnapshot> documentSnapshotFuture = documentReference.get();

        DocumentSnapshot documentSnapshot = documentSnapshotFuture.get();

        if (documentSnapshot.exists()) {
            return documentSnapshot.toObject(Student.class);
        } else {
            return null;
        }
    }

    public List<Student> getAllStudents() {

        List<Student> students = new ArrayList<>();

        Firestore firestore = FirestoreClient.getFirestore();

        Iterable<DocumentReference> documentReferences = firestore.collection(Constants.COLLECTION_STUDENT).listDocuments();

        documentReferences.forEach(documentReference -> {

            ApiFuture<DocumentSnapshot> documentSnapshotApiFuture = documentReference.get();

            try {
                Student student = documentSnapshotApiFuture.get().toObject(Student.class);
                students.add(student);
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        });

        return students;
    }

    public String updateStudent(String id, Student updatedStudent) throws InterruptedException, ExecutionException {

        Firestore firestore = FirestoreClient.getFirestore();

        ApiFuture<WriteResult> collectionApiFuture = firestore
                .collection(Constants.COLLECTION_STUDENT)
                .document(id)
                .set(updatedStudent);

        return collectionApiFuture.get().getUpdateTime().toString();
    }

    public String deleteStudent(String id) {

        Firestore firestore = FirestoreClient.getFirestore();

        firestore.collection(Constants.COLLECTION_STUDENT).document(id).delete();

        return "Student with id " + id + " was deleted successfully! ";
    }
}