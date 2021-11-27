package id.ac.umn.workoutkuy;

import static android.app.Activity.RESULT_OK;

import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.internal.Storage;
import com.google.android.gms.common.api.GoogleApi;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileFragment extends Fragment {
    private static final int PICK_IMAGE_REQUEST = 1;

    private TextView profileName;
    private Button logoutBtn;
    private CircleImageView profileImg;
    private ImageView editIcon;
    private Uri mImageUri;
    FirebaseDatabase rootNode;
    DatabaseReference reference;
    StorageReference storageReference;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            mImageUri = data.getData();
            if(mImageUri != null) {
                Picasso.get().load(mImageUri)
                        .resize(200, 200)
                        .centerCrop()
                        .into(profileImg);
                StorageReference fileReference = storageReference.child(System.currentTimeMillis()
                        + "." + getFileExtension(mImageUri));

                fileReference.putFile(mImageUri)
                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                Toast.makeText(getContext(), "Upload Successful", Toast.LENGTH_LONG).show();

                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {

                            }
                        });
            } else {
                Toast.makeText(getContext(), "No File Selected", Toast.LENGTH_SHORT).show();
            }

        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        profileName = view.findViewById(R.id.profile_name);
        logoutBtn = view.findViewById(R.id.btnLogout);
        profileImg = view.findViewById(R.id.profile_image);
        editIcon = (ImageView) view.findViewById(R.id.editIcon);

        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
        GoogleSignInAccount signInAccount = GoogleSignIn.getLastSignedInAccount(getContext());

        if(signInAccount != null) {
            rootNode = FirebaseDatabase.getInstance("https://workoutkuy-default-rtdb.asia-southeast1.firebasedatabase.app/");
            reference = rootNode.getReference("users").child(signInAccount.getId());
            storageReference = FirebaseStorage.getInstance().getReference("uploads");

            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    profileName.setText(snapshot.child("name").getValue(String.class));
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });


            Picasso.get().load(signInAccount.getPhotoUrl())
                    .resize(200, 200)
                    .centerCrop()
                    .into(profileImg);
        }

        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestEmail()
                        .build();
                GoogleSignInClient mGoogleSignInClient = GoogleSignIn.getClient(getContext(), gso);
                mGoogleSignInClient.signOut();

                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        editIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final EditText edittext = new EditText(getContext());
                alert.setTitle("Change profile");
                alert.setMessage("Change my name");

                alert.setView(edittext);

                alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        String editName = edittext.getText().toString();
                        reference.child("name").setValue(editName);
                    }
                });

                alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                    }
                });

                alert.show();
            }
        });

        profileImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, PICK_IMAGE_REQUEST);
            }
        });
    }

    private String getFileExtension(Uri uri) {
        ContentResolver cr = getContext().getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(uri));
    }
}