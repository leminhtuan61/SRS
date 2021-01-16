package fa.appcode.controller;


import fa.appcode.entities.Customer;
import fa.appcode.service.CustomerService;
import fa.appcode.service.CustomerServiceImpl;
import fa.appcode.web.controller.CustomerController;
import org.junit.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.View;

import java.util.ArrayList;
import java.util.List;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class CustomerControllerTest {

  @Autowired
  private MockMvc mockMvc;
  @MockBean
  private CustomerService customerService;

  @InjectMocks
  private CustomerController customerController;

  @Mock
  View mockView;

  @BeforeAll
  static void setUpBeforeClass() throws Exception {
    List<Customer> customerList = new ArrayList<Customer>();

    customerList.add(new Customer("ha noi", "Le Minh Tuan", 1));
    customerList.add(new Customer("hcm", "Le Minh Minh", 1));
    customerList.add(new Customer("da nang", "Le Minh Meo", 1));

  }

  @Before
  public void setUp() throws Exception {
    // Process mock annotation
    MockitoAnnotations.initMocks(this);

    // Setup Spring test in standalone mode
    this.mockMvc = MockMvcBuilders.standaloneSetup(customerController)
            .setSingleView(mockView).build();
    // this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
  }

  @Test
  void testSave() throws Exception {

    Customer customer = new Customer("da nang", "Le Minh Meo", 1);

    Mockito.when(customerService.saveCustomer(customer)).thenReturn(customer);

    mockMvc
            .perform(MockMvcRequestBuilders.post("/customer/save")
                    .param("address", customer.getAddress()).param("fullName", customer.getFullName())
                    .param("gender", String.valueOf(customer.getGender()))
            )
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.view().name("actionSaveCustomer"))
            .andExpect(MockMvcResultMatchers.model().attributeExists("customer"));

  }
}
