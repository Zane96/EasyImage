# EasyImage
###运用在安卓开发上提供本地（相机，相册，剪裁，文件），缓存，网络三方图片的快速开发库

### 适用设备：

Android：**15～24**

### 添加依赖：

#### Step1.Add the Jetpack repository to your project build file:

```groovy
allprojects {
		repositories {
			...
			maven { url "https://jitpack.io" }
		}
}
```

#### Step2.Add the dependency：

```groovy
dependencies {
	        compile 'com.github.Zane96:EasyImage:v1.0.3-beta'
}
```

### 使用：

- 相机或者相册中获得图片：

  Step1.

  ```java
  EasyImage easyImage = EasyImage.creat(new ImageProviderBuilder().with(this).useCamera().useCrop(500,500)
              .setGetImageListener("bitmap", new OnGetImageListener() {
                  @Override
                  public void getDataBack(Object o) {
                      imageViewProvide.setImageBitmap((Bitmap) o);
                  }
              }));
  ```

  Step2.

  ```java
  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
          super.onActivityResult(requestCode, resultCode, data);
          easyImage.onActivityResult(requestCode, resultCode, data);
  }
  ```

​	如果你希望在回调中获取URI，可以将OnGetImageListener中的第一个参数改为**“uri”**。

​	如果你希望从相册中获取图片，可以直接将useCamera（）改为**useAlbum（）**。

- 网络加载图片：

  Step1.实现你自己项目里面的Application继承类，并做初始化工作，如下：

  ```java
  public class App extends Application{
      int threadCount = Runtime.getRuntime().availableProcessors() + 1;
      @Override
      public void onCreate() {
          super.onCreate();
          EasyImageLoadConfiguration.getInstance()
                  .setLoadPolicy(new FILOPolicy())
                  .setThreadCount(threadCount)
                  .init(this);
      }
  }
  ```

  加载策略：你可以使用FILOPolicy先进后出加载策略，也可以使用FIFOPolicy先进后出加载策略。

  Step2.

  ```java
  EasyImage.creat(new ImageLoadBuidler().with(this)
                                      .useLruCache()
                                      .setHolderPlace(R.drawable.ic_launcher)
                                      .setError(R.drawable.avatar)
                                      .load(Data.URLS[i])
                                      .into(images[i])).execute();
  ```

  缓存：你可以使用Disk缓存，Lru缓存，Disk+Lru缓存，无缓存以及自定义缓存。自定义缓存中你需要将你自己的缓存类实现**ImageCache**接口。

### 更新日志：
-增加**BitmapPool**，优化Bitmap内存复用。

- v1.0.3-beta：修复Android 24+文件共享权限修改导致的使用Intent传递File Uri报错的bug，修复RecycleView网络加载图片错位的Bug，解决思路可以参考我的这篇博客：[RecycleView加载图片错位](http://zane96.github.io/2015/11/26/%E5%85%B3%E4%BA%8ERecycleView%E6%98%BE%E7%A4%BA%E5%BC%82%E6%AD%A5%E5%8A%A0%E8%BD%BD%E5%9B%BE%E7%89%87%E4%B9%B1%E5%BA%8F%E7%9A%84%E9%97%AE%E9%A2%98/)

## FeedBack

zanebot96@gmail.com
