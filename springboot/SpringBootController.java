package com.index.search;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.TotalHits;
import org.apache.lucene.search.WildcardQuery;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.core.env.Environment;


@RestController
@RequestMapping("/hello")
public class SpringBootController {
	
	public static TopDocs topDocs;
	public static String returnval;
	
	@GetMapping
    public String sayHello(@RequestParam(value="querysearch") String querysearchval) {
     
		String outputLuceneIndexPath = "/Users/jebaraj/IndexOutputJSON/";
//      String outputLuceneIndexPath = "⁨⁨indexFiles/";
//       String testpath = getClass().getClassLoader().getResource("").getPath();
//       testpath = testpath + "⁨⁨indexFiles/";
       
      wildcardSearchOnIndex(outputLuceneIndexPath,querysearchval);
//      return "Hi";
      return returnval;
	}


public static void wildcardSearchOnIndex(String outputLuceneIndexPath, String searchString)
{
//String outputLuceneIndexPath = "/Users/jebaraj/IndexOutputJSON/";

try 
{

Path indexPath = Paths.get(outputLuceneIndexPath);
Directory dir = FSDirectory.open(indexPath);
IndexReader indexReader = DirectoryReader.open(dir);

final IndexSearcher indexSearcher = new IndexSearcher(indexReader);

long startTime = System.currentTimeMillis();

//******* sample wildcard search ******* //
String searchString1 = "*" + searchString + "*";
Term term = new Term("description", searchString1);

//create the term query object
Query query = new WildcardQuery(term);

System.out.println("\nSearching for '" + query + "' using WildcardQuery");
           
int hitsPerPage = 10;
IndexReader reader = DirectoryReader.open(dir);
IndexSearcher searcher = new IndexSearcher(reader);
 
TopDocs docs = indexSearcher.search(query,hitsPerPage);
ScoreDoc[] hits = docs.scoreDocs;

System.out.println("Found " + hits.length + " hits.");

// 4. display results
JSONArray ab = new JSONArray();

for(int i = 0; i<hits.length; ++i) {
	System.out.println("this is jeba");
    int docId = hits[i].doc;
    Document d = searcher.doc(docId);

    JSONObject person = new JSONObject();
    person.put("name",d.get("name"));
    person.put("loc",d.get("loc"));
    person.put("text", d.get("text"));
    person.put("description", d.get("description"));
    person.put("doc_id", d.get("doc_id"));
    
    
    ab.add(i, person);
    System.out.println("#######JSON array as string########"+ab.toString());

}
System.out.println("#######JSON array as string########"+ab.toString());
returnval = ab.toString();

reader.close();

//System.out.println("\nSearching for '" + query + "' using WildcardQuery");

long endTime = System.currentTimeMillis();

System.out.println("");
System.out.println("************************************************************************");
System.out.println(docs.totalHits + " documents found. Time taken for the search :" + (endTime - startTime) + "ms");
System.out.println("************************************************************************");
System.out.println("");

} 
catch (Exception e) {
e.printStackTrace();
}    	
}
}



