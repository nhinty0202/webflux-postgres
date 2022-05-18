# Derived from official mysql image
FROM postgres:14.2

# Environment variables
ENV POSTGRES_DB productdb
ENV POSTGRES_USER postgres
ENV POSTGRES_PASSWORD 0202

# Add the content of the scripts/ directory to our image
# All scripts in docker-entrypoint-initdb.d/ are automatically executed during container startup
COPY ./init.sql/ /docker-entrypoint-initdb.d/
