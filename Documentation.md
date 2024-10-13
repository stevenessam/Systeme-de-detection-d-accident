## Docker



To launch docker-compose.yml here are the commands: 
```
docker-compose up
```

Permissions to set:

```
sudo chmod -R 777 ./node-red-data
sudo chmod -R 777 ./grafana-data
sudo chmod -R 777 ./influxdb-data
```

If you need to build the docker-compose:

```
docker-compose up --build

```
Link: 
- http://localhost:3000/ : Grafana
- http://localhost:8086/ : InfluxDB
- http://localhost:1880/ : NODE-Red

