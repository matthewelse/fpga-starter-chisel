#!/usr/bin/env bash

if [ ! -z "$BUILD_SERVER" ]; then
    echo "Building on remote server $BUILD_SERVER"

    # rsync this directory with the server
    if [ -z "$BUILD_REMOTE_USER" ]; then
        echo "WARNING: Remote user not specified."
    elif [ -z "$BUILD_REMOTE_DIRECTORY" ]; then
        echo "ERROR: Remote directory not specified."
        exit 1
    fi

    rsync -crutzS -P --exclude-from=".gitignore" . $BUILD_REMOTE_USER@$BUILD_SERVER:$BUILD_REMOTE_DIRECTORY

    # Prevent incorrectly configured environment variables from creating a loop of doom.
    if [ -f .build_lock ]; then
        echo "ERROR: Build lock file already exists"
        exit 1
    else
        touch .build_lock
        ssh $BUILD_REMOTE_USER@$BUILD_SERVER "cd $BUILD_REMOTE_DIRECTORY && export BUILD_AND_PROGRAM=$BUILD_AND_PROGRAM && \$SHELL -c 'source ~/.zshrc; ./build.sh'" 
        rm .build_lock
    fi
else
    echo "Building locally on `hostname`"

    # This probably ought to be in a Makefile
    # we should also run tests at this point, rather than just building
    sbt 'test:runMain example.BoringAcceleratorBuilder' 

    if [ $? -ne 0 ]; then
        echo "ERROR: verilog generation failed."
        exit 1
    else
        cd quartus
        make

        if [ -z "$BUILD_AND_PROGRAM" ]; then
            make program
        fi
    fi
fi


