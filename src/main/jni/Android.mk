LOCAL_PATH:=$(call my-dir)

include $(CLEAR_VARS)
LOCAL_MODULE:=JNIexample
LOCAL_SRC_FILES:=NativeMethods.c

LOCAL_LDLIBS:=-llog

include $(BUILD_SHARED_LIBRARY)