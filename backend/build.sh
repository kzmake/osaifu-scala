#!/usr/bin/env bash

IFS=':' read -ra imageSplit <<< "$IMAGE"
IMGNAME=${imageSplit[0]}
IMGTAG=${imageSplit[1]}
if [ "$PUSH_IMAGE" == "true" ]
then
  sbt "set ThisBuild / version := \"$IMGTAG\"" "Docker / publish"
  docker tag "$IMGNAME:$IMGTAG" "$IMGNAME:latest"
  docker push "$IMGNAME:latest"
else
  sbt "set ThisBuild / version := \"$IMGTAG\"" "Docker / publishLocal"
fi
