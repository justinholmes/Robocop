package application

import Services._

object Global {
  implicit val streamingService = (new StreamingServiceImpl)
  implicit val streamingProcessor = (new StreamProcessorImpl)
  implicit val storageService = (new StorageServiceImpl)
  implicit val tweetProcessor = (new TweetProcessorImpl)
}
