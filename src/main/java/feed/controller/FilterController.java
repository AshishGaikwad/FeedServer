package feed.controller;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import feed.dao.FiltersDao;
import feed.entity.FilterEntity;
import feed.util.AppUtil;

@RestController
@RequestMapping("/filters")
public class FilterController {
	
	@Autowired
	private FiltersDao lFiltersDao ;
	@Autowired
	private AppUtil appUtil;
	
	@RequestMapping(method = RequestMethod.GET,path = "/get",produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<?> getFilter() {
		JSONObject response = new JSONObject();
		try {
			List<FilterEntity> pFiltersList = lFiltersDao.getFilters();
			if(pFiltersList != null) {
				response.put("status", true);
				response.put("responseCode", 200);
				response.put("responseMsg", "FILTER FETCHED SUCCESSFULLY");
				response.put("body", new JSONArray(appUtil.getGson().toJson(pFiltersList)));
			}else {
				response.put("status", true);
				response.put("responseCode", 201);
				response.put("responseMsg", "FAILED TO FETCH FILTER");
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.put("status", true);
			response.put("responseCode", 203);
			response.put("responseMsg", "SOMETHING WENT WRONG");
		}		
		return new ResponseEntity<String>(response.toString(), HttpStatus.OK);
		
	}
	
	
	@RequestMapping(method = RequestMethod.POST,path = "/save")
	private ResponseEntity<?> addFilter(@RequestBody FilterEntity pFilterEntity) {
		JSONObject response = new JSONObject();
		try {
			pFilterEntity = lFiltersDao.addFilters(pFilterEntity);
			if(pFilterEntity != null) {
				response.put("status", true);
				response.put("responseCode", 200);
				response.put("responseMsg", "FILTER ADDED SUCCESSFULLY");
				response.put("body", new JSONObject(appUtil.getGson().toJson(pFilterEntity)));
			}else {
				response.put("status", true);
				response.put("responseCode", 201);
				response.put("responseMsg", "FAILED TO ADD FILTER");
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.put("status", true);
			response.put("responseCode", 203);
			response.put("responseMsg", "SOMETHING WENT WRONG");
		}		
		return new ResponseEntity<String>(response.toString(), HttpStatus.OK);
		
	}
	

}
