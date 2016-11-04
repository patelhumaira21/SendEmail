package com.example.spring.services.api;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.spring.services.rest.EmailController;
import com.example.spring.services.utils.JsonUtils;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;

public class FirebaseApi {
	private static Logger LOGGER = LoggerFactory.getLogger(FirebaseApi.class);
	private Firebase firebaseRef;
	private ArrayNode result = null;

	public FirebaseApi(String firebaseUrl) {
		firebaseRef = new Firebase(firebaseUrl);
	}

	public void saveInDataBase(ObjectNode data) {
		Firebase postRef = firebaseRef.child("Messages");
		Firebase newPostRef = postRef.push();
		newPostRef.setValue(data);
		String postId = newPostRef.getKey();
		if (postId != null) {
			LOGGER.info("Record Inserted");
		} else {
			LOGGER.error("Error while inserting record in firebase.");
		}
	}

	public ArrayNode getData(String email) {

		Query queryRef = firebaseRef.orderByChild("from").equalTo(email);
		queryRef.addListenerForSingleValueEvent(new ValueEventListener() {
			public void onDataChange(DataSnapshot dataSnapshot) {
				Iterable<DataSnapshot> iterator = dataSnapshot.getChildren();
				ArrayNode array = JsonUtils.getMapper().createArrayNode();
				for (DataSnapshot d : iterator) {
					Map<String, Object> map = (Map<String, Object>) d
							.getValue();
					JsonNode node = JsonUtils.getMapper().convertValue(map,
							JsonNode.class);
					array.add(node);
				}
				result = array;
				System.out.println(result);
			}

			public void onCancelled(FirebaseError firebaseError) {
			}
		});
		while (result == null) {
			System.out.println(result);
		}
		return result;
	}

}
