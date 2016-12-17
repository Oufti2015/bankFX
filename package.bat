@echo off
set java_home=c:\zt974\apps\java\jdk1.8.0_102

%java_home%\bin\javapackager -createjar -appclass sst.bank.OuftiBankFX -srcdir target\classes -outdir out -outfile OuftXBank -v

rem -makeall -appclass sst.bank.OuftiBankFX -name OuftXBank
rem -deploy -outdir outdir 
rem   -name OuftXBank -appclass sst.bank.OuftiBankFX -v -srcdir compiled