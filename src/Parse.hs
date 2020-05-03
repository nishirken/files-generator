{-# LANGUAGE OverloadedStrings #-}
module Parse where

import Config
import qualified Data.Vector as V
import Data.Yaml

instance FromJSON Config where
  parseJSON (Object obj) = Config
    <$> obj .: "sets"
