package refactory;

public class Employee2 {
//	private int _type;
	
	// 5. ��Դ���н���һ���ֶ�, ���Ա����½���״̬����
	private EmployeeType _type;

	public Employee2(int type) {
		setType(type);
	}

	// 1. ʹ�� getType() �� _type ���ҷ�װ����
	
	// 6. ����Դ���еĲ�ѯ������ĺ���, ����ѯ����ת����״̬����
	public int getType() {
		return _type.getTypeCode();
	}
	
	// 7. ����Դ���и���Ϊ��������ֵ�ĺ���, ��һ��ǡ����״̬�������ֵ��"����״̬����"���Ǹ��ֶ�
	public void setType(int type) {
//		this._type = type;
		this._type = EmployeeType.newType(type);
	}
	
	// 8. ʹ�� "Replace Conditional with Polymorphism" ���� payAmount ����
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

// 2. �½�һ����, �������������;Ϊ������(State/Strategy����)
abstract class EmployeeType {
	static final int ENGINEER = 0;
	static final int SALESMAN = 1;
	static final int MANAGER = 2;
	
	int _monthlySalary;
	int _commission;
	int _bonus;
	
	// 4. �ڳ����н���һ������Ĳ�ѯ����, ���Է���������
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

// 3. ���state/strategy�����������, ÿ�������Ӧһ��״̬��
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
