protoc --proto_path=./ --java_out=../../java/ ./Command.proto


cd current_dir
protoc --proto_path=./ --java_out=../../java/ ./*.proto