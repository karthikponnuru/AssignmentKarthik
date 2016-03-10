package edu.umkc.karthik.MDexec;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.WriteResult;

import edu.umkc.karthik.beans.User;


@Path("/user")
public class CRUD {
	

	@GET
	@Path("username/{username}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUser (@PathParam ("username") String username){
		MongoClientURI uri = new MongoClientURI("mongodb://root:Usa%401234@ds023398.mlab.com:23398/practise");
		MongoClient client = new MongoClient(uri);

		DB db = client.getDB(uri.getDatabase());
		DBCollection users = db.getCollection("collection1");
		BasicDBObject query = new BasicDBObject();
		query.put("name",username);
		DBCursor docs = users.find(query);
		return Response.ok(docs.toArray().toString()).build();
		
	}
	
	@POST
	@Path("/createUser")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public Response createUser(User userBO){
		MongoClientURI uri = new MongoClientURI("mongodb://root:Usa%401234@ds023398.mlab.com:23398/practise");
		MongoClient client = new MongoClient(uri);

		DB db = client.getDB(uri.getDatabase());
		DBCollection users = db.getCollection("collection1");
		BasicDBObject query = new BasicDBObject();
		query.put("name",userBO.getName());
		query.put("email", userBO.getEmail());
		query.put("password", userBO.getPassword());
		WriteResult result = users.insert(query);
		String message = "user creation successful \n"+result.toString();
		return Response.ok(message).build();
	}

	@PUT
	@Path("/update")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public Response updateUser(User details){
		MongoClientURI uri = new MongoClientURI("mongodb://root:Usa%401234@ds023398.mlab.com:23398/practise");
		MongoClient client = new MongoClient(uri);

		DB db = client.getDB(uri.getDatabase());
		DBCollection users = db.getCollection("collection1");
		BasicDBObject query = new BasicDBObject();
		query.put("name",details.getName());
		query.put("email", details.getEmail());
		query.put("password", details.getPassword());
		WriteResult result = users.update(new BasicDBObject().append("name", details.getName()),query);
		String message = "updation successful\n"+result.toString();
		return Response.ok(message).build();
	}
	
	@DELETE
	@Path("/deleteUser/{username}")
	@Produces(MediaType.TEXT_PLAIN)
	public Response remove (@PathParam("username") String username){
		MongoClientURI uri = new MongoClientURI("mongodb://root:Usa%401234@ds023398.mlab.com:23398/practise");
		MongoClient client = new MongoClient(uri);

		DB db = client.getDB(uri.getDatabase());
		DBCollection users = db.getCollection("collection1");
		BasicDBObject query = new BasicDBObject();
		query.put("name",username);
		WriteResult result = users.remove(query);
		String message = "User deletion successful \n"+result.toString();
		return Response.ok(message).build();
	}
}
