
`Java` `Android` `MVVM` `RNN`


### 一、开发环境

`操作系统`：Windows 10

`IDE`：Android Studio 3.14

`构建工具`：Gradle 3.4.0

`VCS`：Git  2.15.0.windows.1

`JDK`：JDK 1.8.0_144

`Android SDK`：API 28

`tensorflow`：1.11.0

`python`：2.7.5

`服务端OS`：centos-release-7-4.1708.el7



### 二、作品概述

*简札* 以诗词为基，以交流为体，赋予诗词更强的烟火气，使之走进普罗大众的手心。让简札的用户切实体会到诗词之美，并从中获得愉悦的精神享受，吸引更多人加入到欣赏、创作诗词的洪流中来。在简札中，用户可以用轻松简单的操作，让诗词绽放出更强的生命力。



### 三、功能简介

- 创作诗词

  可以通过自然语言处理技术和机器学习技术自动生成格律诗、自由诗、藏头诗、藏字诗，藏头诗和藏字诗的生成需要输入种子。输入种子的编辑框位于页面中间的底部。

- 修改诗词

  点击诗词书写框，会弹出弹窗，可以在已有诗的基础上，修改诗词。

- 保存诗词

  保存诗这个功能会将写诗的页面做成海报保存到本地相册

- 更换背景

  在更换背景模块中用户可以在手机相册中自由选择背景图，定制个性化诗词背景。

**功能总结**

​        *简札* 的用户在写诗界面可以自动创作藏头诗、藏字诗、格律诗或自由诗。上方图标有更换背景、保存诗词等功能。

​	藏头诗、藏字诗功能中可以让用户自主选择种子，借此激起用户的阅读、创作和发布的兴趣。

​	本软件的目标受众为全年龄段，操作简单，图标清晰，功能明确。无论是为幼儿启蒙还是为诗词行家带去几分乐趣都是我的荣幸。

​        *简札* 以诗词为基，以交流为体，致力于赋予诗词更强的烟火气，使之走进普罗大众的手心。让简札的用户切实体会到诗词之美，并从中获得愉悦的精神享受，吸引更多人加入到欣赏、创作诗词的洪流中来。在简札中，用户可以用轻松简单的操作，让诗词绽放出更强的生命力。



### 四、可行性分析

- 该软件对手机的硬件要求不高，且适配到了 Android 5.0 系统，已经覆盖了主流设备。同时在技术层面上有着坚实的基础，随着近年来人工智能各方面技术的发展，综合使用上文提到的深度学习，图像处理，文字识别等技术已经成为现实的可能，使用 RNN 模型来训练一个写诗模型并非什么难事。
- 采用前后端分离开发，这里并非指 Web 开发中的*前后端分离*，*后端渲染* 等，而是指将训练好的 Model 使用 flask 部署在了服务器上，前端通过调用接口来获得写诗的结果，相比于把训练好的模型作为资源文件加载到项目中其好处是，一可以减小项目打包后大小，二是可以在不更新前端代码的情况下随时用更好的模型来进行替换。
- 在 Android 客户端方面，使用 Java 及 Kotlin（Google 推荐 Android 开发一级语言），采用 MVVM 设计架构，*简札* 中采用了基于安卓提供的 RecyclerView 控件开发的更为简洁易用的 RecyclerView DSL ，同时还涉及自定义 View 和手绘安卓自定义动态效果。采用模块化开发，各功能模块互相解耦，项目结构及功能高度灵活。
- 本软件以为全年龄段爱好者服务为目的。具有操作简单，图标清晰，功能明确等优良特性，用户不会被繁杂的操作所困扰，在简单的方寸之间，就能体验诗词的无穷乐趣。



### 五、目标群体

​	本软件的目标受众为全年龄段，无论是为幼儿启蒙还是为诗词行家带去几分乐趣都是我的荣幸，考虑到用户中可能诗词爱好者居多，针对爱好者的普遍审美设计了简洁隽永的浅茶色主题，为用户提供清新的视觉感受。

​	如果在未来有机会，功能会逐渐完全，可以与个性化自定义写诗、画图、诗人等相关联，开发出更加强大的*简札*。届时必将吸引更多的用户参与到这款软件中来，则可以建立一个互联分享的圈子，更好更广泛的弘扬传统文化。



### 六、项目展示



### 七、项目结构

- view 包：包含自定义的`PoemDisplayView` ，和 activity ，fragment 子包
- model包：其中的 MainViewModel 是 MVVM 中的核心文件，以 Obserable 形式存放了整个项目中所需要的数据，ModelApi 只负责发起网络请求，不处理数据，不接触 UI ，可以理解为数据提供者，Writer 为实现静态代理所需的接口
- service包：其中为一些网络请求相关代码，回调及异步处理相关代码
- utils包：`CharacterUtil` 为中文检测工具，`StatusUtil` 为调整 Android 顶部状态栏及适配全面屏工具, `HttpUtil` 为我封装的使用 Socket 实现的发起 Get 请求的工具
- application包：其中会维护一些全局变量，同时使用 `WeakReference` 不会造成内存泄漏

```
─java
   └─com
       └─zq
           └─poem
               │  ext.kt
               │  
               ├─application
               │      CommonApplication.java
               │      CommonContext.java
               │      
               ├─model
               │  │  MainViewModel.java
               │  │  ModelApi.java
               │  │  Writer.java
               │  │  
               │  └─user
               │      MainModel.kt
               │      Status.kt
               │      UserApiManager.kt
               │      UserInfoApi.kt
               │          
               ├─service
               │      CoroutineCallAdapter.kt
               │      RefreshableLiveData.kt
               │      RetrofitFactory.kt
               │      RetrofitService.kt
               │      
               ├─util
               │      CharacterUtil.java
               │      CoroutineHandler.kt
               │      ExGlideEngine.kt
               │      GraphicOverlay.java
               │      HttpLoggingInterceptor.java
               │      HttpUtil.java
               │      PermissionUtils.java
               │      PicUtil.kt
               │      StatusUtils.java
               │      TextGraphic.java
               │      
               └─view
                   │  MyPageAdapter.java
                   │  PoemDisplayView.java
                   │  
                   ├─activity
                   │      HomeActivity.java
                   │      MainActivity.java
                   │      PhotoMainActivity.kt
                   │      
                   ├─fragment
                   │      AcrosticFragment.java
                   │      FreeFragment.java
                   │      LazyFragment.java
                   │      MeaningFragment.java
                   │      RhymeFragment.java
                   │      
                   └─item
                           PhotoItem.kt
                            
   
```



### 八、细节实现

#### 1. Singleton Design Pattern

- 对于全局性的工具类，使用单例设计模式，如 `MainViewModel`，而 HttpUtil 在该项目中因为异步进行网络请求会出现并发访问的情况，为了线程安全和效率，使用 Double Check
- 对于 `StatusUtils` ,由于只会在窗口创建时使用一次，则没有使用单例模式，而是使用完毕及时释放，避免资源占用

```java
public class HttpUtil {

    /**host of my serve*/
    private static final String BASE_URL = "http://sgxm.tech";

    /** Singleton design pattern */
    private static volatile HttpUtil ourInstance;

    /** stream writer */
    private BufferedWriter bufferedWriter;

    /** stream reader */
    private BufferedReader bufferedReader;

        /**
         * double check thread-safe and efficient
         *
         * @return the instance of this class
         */
    public static HttpUtil getInstance() {
        if (ourInstance == null) {
            synchronized (HttpUtil.class) {
                if (ourInstance == null) {
                    ourInstance = new HttpUtil();
                }
            }
        }
        return ourInstance;
    }

    private HttpUtil() {}
    .
    .
    .
}
```



#### 2. Observer Design Pattern

- 无论时 Android，开发还是 Windows 开发，都有一个规定—— 禁止在非主线程内更新 UI, 禁止在非主线程内发起网络请求，因为这有可能会阻塞线程，而在这种规定下观察者模式可以很好的实现网络数据的获取与UI更新，这也是 MVVM 架构中很重要的一点
- LiveData 作为 Observable，存放这我们需要的数据，即诗歌，当其 value 发生变化时，Observer 会收到消息并更新 UI，其中线程放到后面再讲

**Observable**

```java
 /**
   * Observe design pattern
   *
   * Livedata is a kind of data with lifecycle
   * I use it to save verse request from the serve
   * when its value changes, my observer will update UI
   * and according with the lifecycle of UI component
   */
  private MutableLiveData<List<String>> freeData = new MutableLiveData<>();
  private MutableLiveData<List<String>> rhymeData = new MutableLiveData<>();
  private MutableLiveData<List<String>> arcrosticData = new MutableLiveData<>();
  private MutableLiveData<List<String>> meaningData = new MutableLiveData<>();

  /**
   * livedata which saves the status of network request,when its value changes to false
   * the observer will toast message
   */
  private MutableLiveData<Boolean> status = new MutableLiveData<>();
```

**Observer**

```java
MainViewModel.getInstance().getFreeData().observe(this, new Observer<List<String>>() {
            @Override
            public void onChanged(@Nullable List<String> strings) {
                poemDisplayView.setVerses(strings);
            }
        });

. . .

status.observeForever(new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                if (!aBoolean)
                    Toast.makeText(CommonContext.getCommonContext(), "写得太累了,休息一下", Toast.LENGTH_SHORT).show();
            }
        });
. . .
```



#### 3. Static Proxy Design Pattern

- 最经典的一个例子就是 `Thread` 和 `Runnable` ，代理类和被代理类都实现了相同的接口，而代理类持有被代理对象的引用，静态代理最大的好处就是可以让被代理对象专心去完成它的工作而不需要操心其它
- `Writer` 为两者都需实现的接口，`ModelApi` 为被代理类,其所需要做的事就是专心去连接服务器获取数据，`MainViewModel` 为代理类

**Interface**

```java
public interface Writer {
    void write(final String path, final ModelApi.TYPE type, final String hint, final int times);
}
```

**Proxy**

```java
public class MainViewModel implements Writer {
	/**
     * Static Proxy design pattern
     *
     * MODELAPI is the real object and MainViewModel is the proxy
     * both of them implements the interface Writer
     */
    private static final Writer MODELAPI = new ModelApi();
	. . .
	@Override
    public void write(String path, ModelApi.TYPE type, String hint, int times) {
    	. . . 
        MODELAPI.write(path, type, hint, times);
        . . .
	}
}
```

**Real Object**

```java
public class ModelApi implements Writer {
    . . .
	@Override
    public void write(final String path, final TYPE type, final String hint, final int times) {
     	. . .
    }
}
```



#### 4. Adapter Design Pattern

- Adapter 实际上就是一种标准化的过程，在某些时候可以实现和接口同样的功能，就是来约束你的数据输入，或者是实物对象，让其具有某种特性，符合标准
- 4个 Fragment ，即4个子窗口是我的输入，我需要使用 Adapter，是他们能够与给定的 ViewPager 想适应，符合其使用标准，达到最后可以实现滑动的效果

**Adapter**

```java
public class MyPageAdapter extends FragmentPagerAdapter {

    /** context will be used*/
    private Context context;

    /** lists of window */
    private List<Fragment> fragmentList;

    /** list of window's title*/
    private List<String> list_Title;


        /**
         * main constructor
         *
         * @param fm manager to manage fragments
         * @param context context will be used
         * @param fragmentList list of fragment
         * @param list_Title list of fragment's title
         */
    public MyPageAdapter(FragmentManager fm, Context context, List<Fragment> fragmentList, List<String> list_Title) {
        super(fm);
        this.context = context;
        this.fragmentList = fragmentList;
        this.list_Title = list_Title;

    }
    @Override
    public int getCount() {
        return fragmentList.size();
    }
    
    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }
    
    @Override
    public CharSequence getPageTitle(int position) {
        return list_Title.get(position);
    }
}
```



#### 5. Lazy Load

- `LazyFragment ` 为顶级抽象类，有四个子类，且均为懒加载模式，在 Android 中, 当某个页面还未被访问时，就不去加载它，可以很大程度上提到 APP 的启动速度

```java
 ├─LazyFragment
 	│—— FreeFragment
 	│—— AcrosticFragment
 	│—— RhymeFragment
 	│—— MeaningFragment
```

**LazyFragment**

```java
public abstract class LazyFragment extends Fragment {
    
    . . . 
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)

    {
        if (mRootView != null) {
            isViewCreated = true;
            return mRootView;
        }

        Context context = inflater.getContext();
        FrameLayout root = new FrameLayout(context);

        mViewStub = new ViewStubCompat(context, null);
        mViewStub.setLayoutResource(getResId());
        root.addView(mViewStub, new 				FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
        root.setLayoutParams(new ViewGroup.MarginLayoutParams(ViewGroup.MarginLayoutParams.MATCH_PARENT, ViewGroup.MarginLayoutParams.MATCH_PARENT));

        mRootView = root;
        if (isUserVisible) {
            realLoad();
        }

        isViewCreated = true;
        return mRootView;
    }
    . . .
    
}
```



#### 6. MVVM

- 涉及网络编程时一定会遇到异步数据更新，问题，这就会涉及到 Message Queue 和线程间的消息处理，而使用 LiveData 配合 ViewModel 这种架构后，会让你的程序可读性更强，再也不会出现因为回调，代码被拆分的七零八碎的问题。
- 在以前使用 MVP 架构时，其优点是架构清晰，便于单元测试，但是冗余代码过多，而 MVVM 则十分的灵活，因为其思想类似于 JS 中的 React，跟偏向与一种数据驱动型架构，可以实现响应式，同时十分的灵活，充分结合了 Android 中各个组件的生命周期问题. 与 Windows 不同，Android 中的 UI 引用很容易被 GC 回收调（如切到后台，或被其它弹窗覆盖），而网络请求的回调去更新 UI 时就会出现空指针异常，我们就需要十分关心各个 UI 控件的声明周期



#### 7. PoemDisplayView

- APP 中只使用了窗口控件及 TextView，EditTextView 来显示，输入内容
- `PoemDisplayView` 完全根据数据自行计算绘制，为盒子模型, 并实现了逐字显示动画效果
- 动画效果原理为先在画布上绘制文字，后再绘制矩形覆盖文字，根据XForMode ,取两次绘制的交集显示文字，逐列绘制，原理与 VGA 视频信号类似，去不停的扫描整个画布，扫描过的区域就显示内容
- 真的很复杂, 也很有意思

**Diagram**

```java
public class PoemDisplayView extends View {

    /**
     * padding                                     padding
     *  Left    col                                 Right
     *  |   | Spacing |         |         |         |   |
     *  +---+---------+---------+---------+---------+---+
     *  |   |                                       |   | padding Top
     *  +---+---------------------------------------+---+ ---
     *  |   | colWidth                              |   |
     *  |   |    _                                  |   |
     *  |   |   |x|  col   x         x        x     |   |
     *  |   |   |x| Height x         x        x     |   | Content Height
     *  |   |   |x|        x         x        x     |   |
     *  |   |   |x|        x         x        x     |   |
     *  |   |   |x|        x         x        x     |   |
     *  |   |    -                                  |   |
     *  +---+---------------------------------------+---+ ---
     *  |   |                                       |   | padding Bottom
     *  +---+---------+---------+---------+---------+---+
     *      |              ContentWidth             |
     */
    . . .
}

```

**Draw**

```java
protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        cols = verses.size();
        if (cols > 0) {

            singleVerseLength = verses.get(0).length();
            contentWidth = getWidth() - paddingLeft - paddingRight;
            contentHeight = getHeight() - paddingTop - paddingBottom;
            colSpacing = (contentWidth - cols * mVersePaint.getTextSize()) / (cols - 1);
            colWidth = mVersePaint.getTextSize();
            colHeight = (singleVerseLength + 2) * mVersePaint.getTextSize();


            srcBitmap = Bitmap.createBitmap(contentWidth, (int) colHeight, Bitmap.Config.ARGB_8888);
            srcCanvas = new Canvas(srcBitmap);
            shaderBitmap = Bitmap.createBitmap(contentWidth, (int) colHeight, Bitmap.Config.ARGB_8888);
            shaderCanvas = new Canvas(shaderBitmap);


            //draw verse
            for (int i = 0; i < cols; ++i) {
                srcCanvas.save();
                srcCanvas.translate(i * (colSpacing + colWidth), 0);
                StaticLayout staticLayout = new StaticLayout(verses.get(i), mVersePaint, (int) mVersePaint.getTextSize(), Layout.Alignment.ALIGN_CENTER, 1, 0, true);
                staticLayout.draw(srcCanvas);
                srcCanvas.restore();
            }
            //draw dst(shader) bitmap
            //draw transparent
            mShadowPaint.setColor(Color.TRANSPARENT);
            RectF rectF = new RectF(0, 0, contentWidth, colHeight);
            shaderCanvas.drawRect(rectF, mShadowPaint);
            //draw black
            mShadowPaint.setColor(Color.BLACK);
            int drawCols = (int) Math.ceil(postIndex / colHeight);
            for (int i = 0; i < drawCols; ++i) {
                shaderCanvas.save();
                shaderCanvas.translate(i * (colSpacing + colWidth), 0);
                rectF = new RectF(0, 0, colWidth, postIndex - i * colHeight);
                shaderCanvas.drawRect(rectF, mShadowPaint);
                shaderCanvas.restore();
            }


            //combine
            beforeDraw();
            mShadowPaint.setXfermode(xformode);
            srcCanvas.drawBitmap(shaderBitmap, 0, 0, mShadowPaint);
            mShadowPaint.setXfermode(null);

            //draw final bitmap
            int top;
            if (colHeight >= contentHeight)
                top = 0;
            else
                top = (getHeight() - (int) colHeight) / 2;
            canvas.drawBitmap(srcBitmap, paddingLeft, top, null);
            if (postIndex < cols * colHeight) {
                postIndex += 5;
                postInvalidateDelayed(4);
            } else {
                postIndex = 0;
            }
        }

    }
```



#### 8. Socket

- 后期也会改为使用第三方网络框架，更稳定且更便捷，这次只是想试一下，该模块高度解耦，可以做到无感知替换

- 服务器域名为 `sgxm.tech` 端口号为 `5000` , api 为`/poem` ,支持的参数有`style` `start` ,服务器也为自己搭建，同时自己的博客也在上面
- 将 BaseUrl 定义为常量，对 api 和 Query 参数进行拼接，组成完成的 URL，使用流操作发送读取数据

```java
/**
         * Socket coding
         *
         * Use socket to connect my serve and send http get request
         * then send data back to my model
         *
         * @param path request api
         * @param port which port will be request
         * @param query query params of get request
         * @return return the response of request which has been clipped
         */
    public String httpGet(String path, int port, Map<String, String> query) {
        StringBuilder url = new StringBuilder(BASE_URL + ":" + String.valueOf(port) + path);
        if (!query.isEmpty()) {
            url.append("?");
            for (Map.Entry<String, String> entry : query.entrySet()) {
                url.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
            }
            url.deleteCharAt(url.length() - 1);
        }
        StringBuilder res = new StringBuilder();
        try {
            SocketAddress dest = new InetSocketAddress(new URL(url.toString()).getHost(), port);
            Socket socket = new Socket();
            socket.connect(dest);
            OutputStreamWriter streamWriter = new OutputStreamWriter(socket.getOutputStream());
            bufferedWriter = new BufferedWriter(streamWriter);
            bufferedWriter.write("GET " + url + " HTTP/1.0\r\n");
            bufferedWriter.write("Host:" + BASE_URL + "\r\n");
            bufferedWriter.write("\r\n");
            bufferedWriter.flush();
            BufferedInputStream bufferedInputStream = new BufferedInputStream(socket.getInputStream());
            bufferedReader = new BufferedReader(new InputStreamReader(bufferedInputStream, "utf-8"));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                res.append(line);
            }
            bufferedReader.close();
            bufferedWriter.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return res.substring(res.indexOf("GMT") + 3);
}
```



#### 9. Reflect

- 字节码文件是 Java 中的核心，只要获取到一个类的字节码文件，我们可以对这个类及其对象进行任何修改和访问，包括动态代理等等，我们还可以使用反射来 Hook Android 系统, 实现很多功能，具体就不说了
- 在 Android 中很多 UI 控件的某些属性没有提供修改的 API, 而作为开发者，就可以通过反射，获取它的字节码文件，修改特定域的值，来达到你的要求
- 下面两个方法，分别是我使用反射来修改 `Tab` 的字体，`Indicator` 的颜色和长度，反射中涉及数组，内部类等，需要多次反射才能访问到我想要的属性

```java
  /**
         * Reflect
         *
         * I use reflect to modify some property of the UI components
         * which didn't provide api to modify it, such as typeface
         */
    private void reflectTypeface() {
        ArrayList<TabLayout.Tab> tabs;
        Class tabLayoutClazz = tabLayout.getClass();
        try {
            Field tabsField = tabLayoutClazz.getDeclaredField("mTabs");
            tabsField.setAccessible(true);
            tabs = (ArrayList<TabLayout.Tab>) tabsField.get(tabLayout);
            for (TabLayout.Tab tab : tabs) {
                Class tabClass = tab.getClass();
                Field tabViewField = tabClass.getDeclaredField("mView");
                tabViewField.setAccessible(true);
                View tabView = (View) tabViewField.get(tab);
                Class tabViewClazz = tabView.getClass();
                Field textViewField = tabViewClazz.getDeclaredField("mTextView");
                textViewField.setAccessible(true);
                TextView cusTextView = (TextView) textViewField.get(tabView);
                cusTextView.setTypeface(Typeface.createFromAsset(getAssets(), "FZCSJW.TTF"));
            }

        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

        /**
         * Reflect
         *
         * @see #reflectTypeface()
         * modify TabLayout Strip color and length
         */
    private void reflectLengthColor() {
        Class<?> tabLayoutClazz = tabLayout.getClass();

        Field tabStrip = null;
        try {
            tabStrip = tabLayoutClazz.getDeclaredField("mTabStrip");
            tabStrip.setAccessible(true);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }


        LinearLayout llTab = null;
        try {
            llTab = (LinearLayout) tabStrip.get(tabLayout);
            Method method = llTab.getClass().getDeclaredMethod("setSelectedIndicatorColor", int.class);
            method.setAccessible(true);
            method.invoke(llTab, getResources().getColor(R.color.colorLine));
        } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
        }

        int left = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, Resources.getSystem().getDisplayMetrics());
        int right = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, Resources.getSystem().getDisplayMetrics());

        for (int i = 0; i < (llTab != null ? llTab.getChildCount() : 0); i++) {
            View child = llTab.getChildAt(i);
            child.setPadding(0, 0, 0, 0);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1);
            params.leftMargin = left;
            params.rightMargin = right;
            child.setLayoutParams(params);
            child.invalidate();
        }
    }
```



#### 10. $\lambda$ 表达式

- Java 8 支持 $\lambda$ 表达式后，许多匿名内部类都可以进行简写，**极大的减少代码行数**，提高开发效率
- 在 Kotlin 中，$\lambda$ 表达式的运用更是广泛，可以使用高阶函数来实现许多功能，取代了Java中的方法引用，其本质思想就是 '形式化方法' 中所提到的 **$\lambda$ 演算** ，表达的即为对变量的约束，及运算关系

```java
tv_enter.setOnClickListener((v)->{
        Intent intent = new Intent(MainActivity.this, HomeActivity.class);
        startActivity(intent);
        finish();
});
. . .
MainViewModel.getInstance().getArcrosticData().observe(this, (string) -> {
                poemDisplayView.setVerses(strings);
});
```

---
## License
```
Script is licensed under MIT license with one exception:
Do not create a public WordPress plugin based on it, as I will develop it. 
If you need to use it for a public WordPress plugin right now, please ask me by email first. Thanks!

Attribution is not required, but much appreciated, especially if you’re making a product for developers.
```



