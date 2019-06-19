//
// Created by Toreo1 on 11/06/2019.
//
#include <string.h>
#include<stdio.h>
#include <stdlib.h>
#include <fcntl.h>
#include <unistd.h>
#include <termios.h>
#include <sys/mman.h>
#include <errno.h>
#include <jni.h>
#include <com_example_theflyingfishgameapp_FlyingFishView.h>
#include <com_example_theflyingfishgameapp_GameOverActivity.h>
#include <com_example_theflyingfishgameapp_GameOverActivity_OnKeyListener.h>
#include <com_example_theflyingfishgameapp_SplashActivity.h>


JNIEXPORT jstring JNICALL Java_com_example_jniexample_MainActivity_GetMyString(JNIEnv *env, jobject obj)
{
    /*int fd = open("/dev/fpga_led", O_RDWR);
    int ret= write(fd,&counter, sizeof(counter));
    close(fd);
    counter++;
    if(counter>8)
        counter=0;*/


    //return env->NewStringUTF("Hello world from JNI");
}

JNIEXPORT jint JNICALL Java_com_example_theflyingfishgameapp_FlyingFishView_PiezoControl(JNIEnv *env, jobject thiz,jint value)
{
    int fd,ret;
    int data=value;
    fd=open("/dev/fpga_piezo",O_WRONLY);
    if(fd <0) return -errno;
    ret=write (fd,&data,1);
    close(fd);
    if(ret==1) return 0;
    return -1;
}

JNIEXPORT jint JNICALL Java_com_example_theflyingfishgameapp_FlyingFishView_LED(JNIEnv *env, jobject thiz,jint value)
{
    unsigned short val;
    if (value==3)
    {
        val=7;
        int fd = open("/dev/fpga_led", O_WRONLY);
        write(fd, &val, sizeof(val));
        close(fd);
    }
    else if(value==0)
    {
        val=0;
        int fd = open("/dev/fpga_led", O_WRONLY);
        write(fd, &val, sizeof(val));
        close(fd);
    }
    else if(value==2)
    {
        val = 3;

        int fd = open("/dev/fpga_led", O_WRONLY);
        write(fd, &val, sizeof(val));
        close(fd);
    }
    else if(value==1)
    {
        val = 1;

        int fd = open("/dev/fpga_led", O_WRONLY);
        write(fd, &val, sizeof(val));
        close(fd);
    }


    return 0;

}

#define FULL_LED1	9
#define	FULL_LED2	8
#define FULL_LED3	7
#define FULL_LED4	6
#define ALL_LED		5

JNIEXPORT jint JNICALL Java_com_example_theflyingfishgameapp_FlyingFishView_CLED(JNIEnv* env, jobject thiz, jint led_num, jint val1, jint val2, jint val3)
{
    int fd,ret;
    char buf[3];

    fd = open("/dev/fpga_fullcolorled", O_WRONLY);
    if (fd < 0)
    {
        exit(-1);
    }
    ret = (int)led_num;
    switch(ret)
    {
        case FULL_LED1:
            ioctl(fd,FULL_LED1);
            break;
        case FULL_LED2:
            ioctl(fd,FULL_LED2);
            break;
        case FULL_LED3:
            ioctl(fd,FULL_LED3);
            break;
        case FULL_LED4:
            ioctl(fd,FULL_LED4);
            break;
        case ALL_LED:
            ioctl(fd,ALL_LED);
            break;
    }
    buf[0] = val1;
    buf[1] = val2;
    buf[2] = val3;

    write(fd,buf,3);

    close(fd);
}

JNIEXPORT jint JNICALL Java_com_example_theflyingfishgameapp_GameOverActivity_CLED(JNIEnv* env, jobject thiz, jint led_num, jint val1, jint val2, jint val3)
{
    int fd,ret;
    char buf[3];

    fd = open("/dev/fpga_fullcolorled", O_WRONLY);
    if (fd < 0)
    {
        exit(-1);
    }
    ret = (int)led_num;
    switch(ret)
    {
        case FULL_LED1:
            ioctl(fd,FULL_LED1);
            break;
        case FULL_LED2:
            ioctl(fd,FULL_LED2);
            break;
        case FULL_LED3:
            ioctl(fd,FULL_LED3);
            break;
        case FULL_LED4:
            ioctl(fd,FULL_LED4);
            break;
        case ALL_LED:
            ioctl(fd,ALL_LED);
            break;
    }
    buf[0] = val1;
    buf[1] = val2;
    buf[2] = val3;

    write(fd,buf,3);

    close(fd);
}