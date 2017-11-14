# Server documentation


Digital Ocean IP: 128.199.61.201

Domain: ctzr.me

Paths:

      home/cityzer/databaseHandler/
            databaseHandler.jar
            
      var/www/html/ - location of data files.

## home/cityzer/fetch-data.sh

      #!/bin/bash
      cp /var/www/html/output.txt /var/www/html/output-old.txt
      cd /home/joel/print/src
      /usr/bin/java com/company/Main > /var/www/html/output.txt
      cp /home/joel/print/src/data.bin /var/www/html/data.bin
      ncdump /var/www/html/data.bin > /var/www/html/dataASCII.ascii
      ncgen -o /var/www/html/dataCDF.nc /var/www/html/dataASCII.ascii
      FILESIZE=$(stat -c%s "/var/www/html/output.txt")
      if [ $FILESIZE -lt 100000 ]; then
             cp /var/www/html/output-old.txt /var/www/html/output.txt
             echo "File retrieval failed!"
      fi
      echo "Size of file = $FILESIZE"
      cd /home/cityzer/databaseHandler
      java -jar databaseHandler.jar

      1. Kopioi olemassaolevan vanhan output.txt:n talteen.
      2. Ei oleellista.
      3. Käynnistää Main-ohjelman print/src/com/companysta ja kirjoittaa syötteen rootin /var/www/html-kansioon
      4. Kopioi Main:in luoman .bin-tiedoston joel/print/src-kansioista rootin var/www/html-kansioon
      5. Käyttää ncdump-komentoa luodakseen ASCII-version .bin-tiedostosta
      6. Luo NetCDF-tiedoston (.nc) ASCII-tiedostosta
      
