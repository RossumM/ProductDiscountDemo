import org.junit.Test;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;

import controller.HomeController;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Merijn
 */
public class ControllerTest {
	
	@Test
    public void testHomePage() throws Exception{
    	HomeController controller = new HomeController();
        System.out.println("test");
        if(controller == null){
            System.out.println("null");
        }
        //try{
        MockMvc mock = standaloneSetup(controller).build();
    	mock.perform(get("/")).andExpect(view().name("index"));
//        }catch(ExceptionInInitializerError er){
//            er.printStackTrace(System.out);
//        }
    }
}
