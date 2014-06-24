SRC_DIR = .
TARGET_DIR = bin
CC = ghc
CFLAGS = --make

run: build
	./$(TARGET_DIR)/TestClientServer

build: $(TARGET_DIR)/TestClientServer

test: $(TARGET_DIR)/test
	./$(TARGET_DIR)/test

$(TARGET_DIR)/TestClientServer: TestClientServer.hs
	$(CC) $^ -o $@

.PHONY: clean
clean:
	rm *.hi *.o
	rm $(TARGET_DIR)/*