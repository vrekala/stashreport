package com.evault.controller;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.evault.form.ContainerForm;
import com.evault.form.Containers;
import com.evault.utils.SkylineUtils;

@Controller
public class ContainersController {

	@RequestMapping(value = "/listcontainers", method = RequestMethod.GET)
	public ModelAndView displayContainer(@RequestParam("token")String token) {

		System.out.println(" Token" +token);
        SkylineUtils utils=new SkylineUtils();

        String jsonTxt=utils.getContainers(token);
        List<Containers> containers = new ArrayList<Containers>();
        
        
		JSONObject mainJson = (JSONObject) JSONSerializer.toJSON(jsonTxt);
		JSONArray jarray = mainJson.getJSONArray("containers");
		for (int i = 0; i < jarray.size(); i++) {
			JSONObject element = jarray.getJSONObject(i);
         	containers.add(new Containers(element.get("name").toString(), element.get("href").toString(), element.get("size").toString()));

		}
		
		
//         	containers.add(new Containers("Barack", "Obama", "barack.o@whitehouse.com"));
//        	containers.add(new Containers("Barack", "Obama", "barack.o@whitehouse.com"));
//        	containers.add(new Containers("Barack", "Obama", "barack.o@whitehouse.com"));
//        	containers.add(new Containers("Barack", "Obama", "barack.o@whitehouse.com"));
//        	containers.add(new Containers("Barack", "Obama", "barack.o@whitehouse.com"));
// 
            
        	ContainerForm containerForm = new ContainerForm();
        	containerForm.setContainers(containers);
             
        

		System.out.println(" containers json " +jsonTxt);
		
		
        return new ModelAndView("displayContainers" , "containerForm", containerForm);
	}

}
