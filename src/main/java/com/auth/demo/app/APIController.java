package com.auth.demo.app;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 *
 */
@RestController
@RequestMapping("/api/v1")
public class APIController {

	private static final Logger log = LoggerFactory.getLogger(APIController.class);

	@RequestMapping(value = "/updateData", method = RequestMethod.PUT, consumes = "application/json")
	@PreAuthorize("#oauth2.hasScope('update')")
	public String updateData(@RequestBody Map<String, Object> formData) {
		log.debug("<<<Enter updateData>>>");
		log.debug("formData: " + formData.toString());
		Map<String, String> demoOutput = new HashMap<String, String>();
		demoOutput.put("Status", "success");
		JSONObject demoJson = new JSONObject(demoOutput);
		// This would be an update - Return Success
		log.debug("<<<Exit updateData>>>");
		return demoJson.toString();

	}

	@PreAuthorize("#oauth2.hasScope('read') or #oauth2.hasScope('update')")
	@RequestMapping(value = "/readData", method = RequestMethod.GET)
	public String readData(@RequestParam("id") String id) throws JSONException {
		log.debug("<<<<Enter readData>>>");
		Map<String, String> demoOutput = new HashMap<String, String>();
		demoOutput.put("id", id);
		demoOutput.put("publicData1", "public1");
		demoOutput.put("publicData2", "public2");
		JSONObject demoJson = new JSONObject(demoOutput);
		log.debug("<<<<Exit readData>>>");
		return demoJson.toString();

	}
}
