package application

import Services._
import sindi.core._

object Global {
  implicit val streamingService = bind[StreamingService](new StreamingServiceImpl)
  implicit val streamingProcessor = bind[StreamProcessor](new StreamProcessorImpl)
  implicit val storageService = bind[StorageService](new StorageServiceImpl)
  implicit val tweetProcessor = bind[TweetProcessor](new TweetProcessorImpl)
}
