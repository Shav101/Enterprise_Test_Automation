package step_definitions;

import org.junit.Assert;
import org.openqa.selenium.Keys;
import org.openqa.selenium.support.ui.Select;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pages.AmazonTestsPage;
import utilities.BrowserUtils;
import utilities.Driver;

public class AmazonTests_steps {

	AmazonTestsPage amazonpage = new AmazonTestsPage();
	BrowserUtils utils = new BrowserUtils();
	
	

	@Given("I am on the amazon homepage")
	public void i_am_on_the_amazon_homepage() throws InterruptedException {
	    Driver.getDriver().get("https://amazon.com");
	    Thread.sleep(3000);
	    String homepageTitle = Driver.getDriver().getTitle();
	    System.out.println(homepageTitle);
	    Assert.assertEquals("Amazon.com", homepageTitle);
	}
	@Given("The departments dropdown is {string}")
	public void the_departments_dropdown_is(String defaultOption) throws InterruptedException {
		Thread.sleep(2000);
		 Select letsSelect = new Select(amazonpage.departmentsDropdown);
		 Assert.assertEquals(letsSelect.getFirstSelectedOption(), defaultOption);
		//Assert.assertEquals(utils.getSelectedOption(ahomepage.departmentsDropdown), defaultOption);
	}
	@When("I select the department {string}")
	public void i_select_the_department(String optionToSelect) {
	    utils.selectByVisibleText(amazonpage.departmentsDropdown, optionToSelect);
	}
	@When("I search {string}")
	public void i_search(String searchItem) {
	    amazonpage.searchField.sendKeys(searchItem + Keys.ENTER);
	}
	@Then("I should be on {string} search result page")
	public void i_should_be_on_search_result_page(String searchedItem) {
	    String searchPageTitle = Driver.getDriver().getTitle();
	    Assert.assertTrue(searchPageTitle.contains(searchedItem));
	}
	
	
}
