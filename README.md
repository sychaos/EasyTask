# EasyTask
做项目时遇到一个老项目，里面有一些需要切换线程的操作，考虑到引入rxjava成本太高且没有必要，所以选择封装一个库用来快速切换线程

## Usage
```java
 EasyTask.create(new EasyTask.OnExecute<List<String>>() {

            @Override
            public List<String> call(TaskManager<List<String>> mTaskManager) {
                List<String> list = new ArrayList();
                list.add("s");
                list.add("2");
                list.add("3");
                return list;
            }
        }).runOn(Processes.background())
          .callbackOn(Processes.mainThread())
          .callback(new DefaultCallback<List<String>>() {
              @Override
              public void onError(Throwable t) {

              }

              @Override
              public void onFinish(List<String> strings) {

              }
          }).run();
```
