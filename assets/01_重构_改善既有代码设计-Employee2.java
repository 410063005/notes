package refactory;

public class Employee2 {
//	private int _type;
	
	// 5. 在源类中建立一个字段, 用以保存新建的状态对象
	private EmployeeType _type;

	public Employee2(int type) {
		setType(type);
	}

	// 1. 使用 getType() 将 _type 自我封装起来
	
	// 6. 调整源类中的查询类型码的函数, 将查询动作转发给状态对象
	public int getType() {
		return _type.getTypeCode();
	}
	
	// 7. 调整源类中负责为类型码设值的函数, 将一个恰当的状态子类对象赋值给"保存状态对象"的那个字段
	public void setType(int type) {
//		this._type = type;
		this._type = EmployeeType.newType(type);
	}
	
	// 8. 使用 "Replace Conditional with Polymorphism" 处理 payAmount 方法
	int payAmount() {
//		switch (getType()) {
//			case ENGINEER:
//				return _monthlySalary;
//			case SALESMAN:
//				return _monthlySalary + _commission;
//			case MANAGER:
//				return _monthlySalary + _bonus;
//			default:
//				throw new RuntimeException("Incorrect Employee");
//		}
		return _type.payAmount();
	}

	int _monthlySalary;
	int _commission;
	int _bonus;
}

// 2. 新建一个类, 根据类型码的用途为它命名(State/Strategy对象)
abstract class EmployeeType {
	static final int ENGINEER = 0;
	static final int SALESMAN = 1;
	static final int MANAGER = 2;
	
	int _monthlySalary;
	int _commission;
	int _bonus;
	
	// 4. 在超类中建立一个抽象的查询函数, 用以返回类型码
	abstract int getTypeCode();
	
	int payAmount() {
		switch (getTypeCode()) {
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
	
	static EmployeeType newType(int code) {
		switch (code) {
		case ENGINEER:
			return new Engineer();

		case SALESMAN:
			return new Salesman();

		case MANAGER:
			return new Manager();
		
		default:
			throw new IllegalArgumentException("Incorrect Employee code");
		}
	}
}

// 3. 这个state/strategy对象添加子类, 每个子类对应一种状态码
class Engineer extends EmployeeType {

	@Override
	int getTypeCode() {
		return ENGINEER;
	}
}

class Salesman extends EmployeeType {

	@Override
	int getTypeCode() {
		return SALESMAN;
	}
}

class Manager extends EmployeeType {

	@Override
	int getTypeCode() {
		return MANAGER;
	}
}
