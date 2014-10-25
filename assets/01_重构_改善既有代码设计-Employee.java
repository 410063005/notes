package refactory;

public class Employee {
	private int _type;
	static final int ENGINEER = 0;
	static final int SALESMAN = 1;
	static final int MANAGER = 2;

	public Employee(int type) {
		_type = type;
	}

	int payAmount() {
		switch (_type) {
			case ENGINEER:
				return _monthlySalary;
			case SALESMAN:
				return _monthlySalary + _commission;
			case MANAGER:
				return _monthlySalary + _bonus;
			default:
				throw new RuntimeException("Incorrect Employee");
		}
	}

	int _monthlySalary;
	int _commission;
	int _bonus;
}
