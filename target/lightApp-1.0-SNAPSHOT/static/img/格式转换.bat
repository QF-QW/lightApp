@echo off

::���·�����Ҫ�������Ƶ����Ƶ��ʽ�������г���һЩ��Ҫ����Ƶ��ʽ
set Ext=*.jpg,*.png,*.jpeg

md webp

echo ��ʼ��Ƶת��

::���·����������ʽ���������Ϊmp4�������и���
for %%a in (%Ext%) do (
	echo ����ת����%%a
	ffmpeg  -i "%%a"  "webp\%%~na.webp" -y
)

echo ת�����

pause