---- Docker command ----

sudo docker run --name best_travel_sql -p 5432:5432 -e  POSTGRES_DB=best_travel -e POSTGRES_USER=alejandro -e POSTGRES_PASSWORD=debuggeandoideas -v ./db/sql/create_schema.sql:/docker-entrypoint-initdb.d/create_schema.sql -v ./db/sql/data.sql:/docker-entrypoint-initdb.d/data.sql postgres:latest