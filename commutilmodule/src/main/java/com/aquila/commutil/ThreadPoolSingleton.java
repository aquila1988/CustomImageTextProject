package com.aquila.commutil;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by yulong_wang on 2017/10/31 12:02.
 */

public class ThreadPoolSingleton {

    private ExecutorService executorService;
    private final int availableProcessor = Runtime.getRuntime().availableProcessors();
    private ThreadPoolSingleton() {
        if (executorService == null) {
            int coreNum = availableProcessor / 2;
            
            // 用单例模式创建线程池，保留2个核心线程，最多线程为CPU个数的2n+1的两倍.
            int maxProcessor = (availableProcessor * 2 + 1) * 2 ;

            executorService = new ThreadPoolExecutor(coreNum > 2 ? 2 : coreNum, maxProcessor,
                    60L, TimeUnit.SECONDS,
                    new SynchronousQueue<Runnable>());
            CLog.debug("availableProcessor = " + availableProcessor);
        }
    }


    private static ThreadPoolSingleton instance;

    public static ThreadPoolSingleton getInstance() {
        if (instance == null) {
            instance = new ThreadPoolSingleton();
        }
        return instance;
    }

    int index = 0;
    public void executeTask(Runnable runnable) {
        executorService.execute(runnable);
        CLog.debug(wrapperContent() + " ，线程执行..." + index++ );
    }


    private String wrapperContent() {
        String SUFFIX = ".java";
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();

        StackTraceElement targetElement = stackTrace[4];
        String className = targetElement.getClassName();
        String[] classNameInfo = className.split("\\.");
        if (classNameInfo.length > 0) {
            className = classNameInfo[classNameInfo.length - 1] + SUFFIX;
        }

        if (className.contains("$")) {
            className = className.split("\\$")[0] + SUFFIX;
        }

        String methodName = targetElement.getMethodName();
        int lineNumber = targetElement.getLineNumber();

        if (lineNumber < 0) {
            lineNumber = 0;
        }

//        String tag = (tagStr == null ? className : tagStr);
//
//        if (mIsGlobalTagEmpty && TextUtils.isEmpty(tag)) {
//            tag = TAG_DEFAULT;
//        } else if (!mIsGlobalTagEmpty) {
//            tag = mGlobalTag;
//        }
//
//        String msg = (objects == null) ? NULL_TIPS : getObjectsString(objects);
        String headString = "【" + className + ":" + lineNumber + ")#" + methodName +"()】 ";

        return headString;
    }

}
