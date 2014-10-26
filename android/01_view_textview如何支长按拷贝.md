[category]: android
[keywords]: android,view
[source]: http://blog.csdn.net/jaycee110905/article/details/8762274
[date]: 2014-10-26

android sdk api level 11 及以上版本中，TextView 增加了 android:textIsSelectable 属性(对应的方法为 isTextSelectable()) 用于支持 "Indicates that the content of a non-editable text can be selected. " 特性， 即 TextView 中的文本内容可以被选中&拷贝。

总结如下：

1. textIsSelectable 缺省为 false
2. 一些类似于联系人地址等信息应设置 textIsSelectable 为 true，方便选中&复制
3. textIsSelectable 设置为 true，实现是将 focusable，focusableInTouchMode， clickable 和 longClickable 设置为 true

具体代码如下：

    /**
     * Sets whether the content of this view is selectable by the user. The default is
     * {@code false}, meaning that the content is not selectable.
     * <p>
     * When you use a TextView to display a useful piece of information to the user (such as a
     * contact's address), make it selectable, so that the user can select and copy its
     * content. You can also use set the XML attribute
     * {@link android.R.styleable#TextView_textIsSelectable} to "true".
     * <p>
     * When you call this method to set the value of {@code textIsSelectable}, it sets
     * the flags {@code focusable}, {@code focusableInTouchMode}, {@code clickable},
     * and {@code longClickable} to the same value. These flags correspond to the attributes
     * {@link android.R.styleable#View_focusable android:focusable},
     * {@link android.R.styleable#View_focusableInTouchMode android:focusableInTouchMode},
     * {@link android.R.styleable#View_clickable android:clickable}, and
     * {@link android.R.styleable#View_longClickable android:longClickable}. To restore any of these
     * flags to a state you had set previously, call one or more of the following methods:
     * {@link #setFocusable(boolean) setFocusable()},
     * {@link #setFocusableInTouchMode(boolean) setFocusableInTouchMode()},
     * {@link #setClickable(boolean) setClickable()} or
     * {@link #setLongClickable(boolean) setLongClickable()}.
     *
     * @param selectable Whether the content of this TextView should be selectable.
     */
    public void setTextIsSelectable(boolean selectable) {
        if (!selectable && mEditor == null) return; // false is default value with no edit data

        createEditorIfNeeded();
        if (mEditor.mTextIsSelectable == selectable) return;

        mEditor.mTextIsSelectable = selectable;
        setFocusableInTouchMode(selectable);
        setFocusable(selectable);
        setClickable(selectable);
        setLongClickable(selectable);

        // mInputType should already be EditorInfo.TYPE_NULL and mInput should be null

        setMovementMethod(selectable ? ArrowKeyMovementMethod.getInstance() : null);
        setText(mText, selectable ? BufferType.SPANNABLE : BufferType.NORMAL);

        // Called by setText above, but safer in case of future code changes
        mEditor.prepareCursorControllers();
    }

    /**
     *
     * Returns the state of the {@code textIsSelectable} flag (See
     * {@link #setTextIsSelectable setTextIsSelectable()}). Although you have to set this flag
     * to allow users to select and copy text in a non-editable TextView, the content of an
     * {@link EditText} can always be selected, independently of the value of this flag.
     * <p>
     *
     * @return True if the text displayed in this TextView can be selected by the user.
     *
     * @attr ref android.R.styleable#TextView_textIsSelectable
     */
    public boolean isTextSelectable() {
        return mEditor == null ? false : mEditor.mTextIsSelectable;
    }


在旧版本的SDK中如何实现类似功能呢？参考以上代码，可使用以下代码在旧版本上实现选中&复制功能，注意这里的 setTextIsSelectableExt 方法可能在新的设备上不能正常工作，所以保险起见：如果 textIsSelectable 或 setTextIsSelectable() 方法存在&可用，直接使用，否则切换到自定义的 setTextIsSelectableExt 方法。

	public class TextViewExt extends TextView {

		public TextViewExt(Context context) {
			super(context);
		}

		public TextViewExt(Context context, AttributeSet attrs) {
			super(context, attrs);
		}

		public void setTextIsSelectableExt(boolean selectable) {
		//		setCursorVisible(false);
		setFocusableInTouchMode(selectable);
		setFocusable(selectable);
		setClickable(selectable);
		setLongClickable(selectable);

		// mInputType should already be EditorInfo.TYPE_NULL and mInput should be null

		setMovementMethod(selectable ? ArrowKeyMovementMethod.getInstance() : null);
		setText(getText(), selectable ? BufferType.SPANNABLE : BufferType.NORMAL);
		}
	}
