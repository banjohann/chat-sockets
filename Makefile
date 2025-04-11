CLIENT_SRC := client/src
SERVER_SRC := server/src
OUT_DIR := out

CLIENT_SOURCES := $(shell find $(CLIENT_SRC) -name "*.java")
SERVER_SOURCES := $(shell find $(SERVER_SRC) -name "*.java")

.PHONY: all
all: compile

.PHONY: compile
compile: compile_client compile_server

.PHONY: compile_client
compile_client:
	mkdir -p client/out
	javac -d client/out $(CLIENT_SOURCES)

.PHONY: compile_server
compile_server:
	mkdir -p server/out
	javac -d server/out $(SERVER_SOURCES)

.PHONY: run_client
run_client: compile_client
	java -cp client/out Main

.PHONY: run_servidor
run_server: compile_server
	java -cp server/out Main

.PHONY: clean
clean:
	rm -rf $(OUT_DIR)
