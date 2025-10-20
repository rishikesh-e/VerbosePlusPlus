if [ -z "$1" ]; then
    echo "Usage: verbose++ <filename>"
    exit 1
fi

FILE="$1"

mkdir -p build
javac -d build/ *.java

java -jar MyInterpreter.jar verbose++ "$1"