package com.example.recipe;


import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import com.example.inventory.model.Inventory;
import com.example.inventory.service.InventoryService;

import org.apache.http.HttpEntity;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.Assert;


@SpringBootTest(classes=RecipeApplicationTests.class)
class RecipeApplicationTests {

	@Test
	void testSuccessfulGetRecipes() throws Exception {
		HttpClient client = HttpClientBuilder.create().build();
		HttpGet request = new HttpGet("http://localhost:8124/recipes");
		org.apache.http.HttpResponse response = client.execute(request);
		HttpEntity entity = response.getEntity();
		String content = EntityUtils.toString(entity);
		
		JSONObject jsonObject = new JSONObject(content);
		Assert.assertNotNull(jsonObject.getJSONArray("recipeNames"));
		Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
	}

	/* This test assumes that at least one Recipe exists in the database */
	@Test
	void testSuccessfulGetRecipeDetails() throws Exception {
		HttpClient client = HttpClientBuilder.create().build();		
		HttpGet request = new HttpGet("http://localhost:8124/recipes");
		org.apache.http.HttpResponse response = client.execute(request);
		HttpEntity entity = response.getEntity();
		String content = EntityUtils.toString(entity);

		JSONObject jsonResultObject = new JSONObject(content);
		String recipeName = jsonResultObject.getJSONArray("recipeNames").get(0).toString();

		HttpClient client2 = HttpClientBuilder.create().build();		
		HttpGet request2 = new HttpGet("http://localhost:8124/recipes/details/" + recipeName);
		org.apache.http.HttpResponse response2 = client2.execute(request2);
		HttpEntity entity2 = response2.getEntity();
		String content2 = EntityUtils.toString(entity2);

		JSONObject detailsJson = new JSONObject(content2);
		Assert.assertNotNull(detailsJson.getJSONObject("details"));
		Assert.assertEquals(response2.getStatusLine().getStatusCode(), 200);
	}

}
