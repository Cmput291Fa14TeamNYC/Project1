package ca.ualberta.cmput291TeamNYC.project1;

public class TestType {

	private int type_id;
	private String test_name;
	private String pre_requirement;
	private String test_procedure;

	public TestType() {
	}

	public int getType_id() {
		return type_id;
	}

	public void setType_id(int type_id) {
		this.type_id = type_id;
	}

	public String getTest_name() {
		return test_name;
	}

	public void setTest_name(String test_name) {
		this.test_name = test_name;
	}

	public String getPre_requirement() {
		return pre_requirement;
	}

	public void setPre_requirement(String pre_requirement) {
		this.pre_requirement = pre_requirement;
	}

	public String getTest_procedure() {
		return test_procedure;
	}

	public void setTest_procedure(String test_procedure) {
		this.test_procedure = test_procedure;
	}
}
