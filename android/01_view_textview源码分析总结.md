[category]: android
[keywords]: android,view
[source]: -
[date]: 2014-10-26

最近在看 View 的代码，简单总结如下：

TextView 相当复杂，在 android 4.2.2 中(api level 17)，其代码量为 9220 行。

Button 直接继承自 TextView， 没有添加任何新的方法，所以代码显得非常简单。Button 与 TextView 的区别居然仅仅在于 defStyle 参数不同。前者缺省时为 com.android.internal.R.attr.buttonStyle，后者缺省为 com.android.internal.R.attr.textViewStyle。

EditText 直接继承自 TextView，添加了少量的方法。它的缺省 defStyle 为 com.android.internal.R.attr.editTextStyle。

通过重新设置 defStyle 参数值，可以很容易地让将 TextView “变成”一个 Button 或是 EditText。代码如下：

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 将 textview 显示成一个 textview
		// com.android.internal.R.attr.textViewStyle = 16842884
		// TextView tv = new TextView(this, null, 16842884);

		// 将 textview 显示成一个 button
		// com.android.internal.R.attr.buttonStyle = 16842824
		// TextView tv = new TextView(this, null, 16842824);

		// 将 textview 显示成一个 editext
		// com.android.internal.R.attr.editTextStyle = 16842862
		TextView tv = new TextView(this, null, 16842862) {

		    @Override
		    protected boolean getDefaultEditable() {
			return true;
		    }

		    @Override
		    protected MovementMethod getDefaultMovementMethod() {
			return ArrowKeyMovementMethod.getInstance();
		    }
		};

		tv.setText("hahah");
		tv.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT));
		setContentView(tv);
	}

