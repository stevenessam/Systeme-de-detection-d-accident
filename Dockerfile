FROM nodered/node-red:latest
USER root
RUN npm install -g --unsafe-perm node-red \
    && npm install node-red-dashboard \
    && npm install node-red-contrib-ui-led \
    && npm install node-red-contrib-web-worldmap \
    && npm install node-red-node-ui-table \
    && npm install node-red-contrib-influxdb
