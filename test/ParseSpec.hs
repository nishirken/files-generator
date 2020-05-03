{-# LANGUAGE OverloadedStrings #-}

module ParseSpec where

import qualified Data.Map as M
import qualified Data.Yaml as Y
import Parse
import Config
import Test.Hspec (describe, it, Spec, shouldBe)

parseSpec :: Spec
parseSpec = describe "ParseSpec" $ do
  it "parse sets" $ do
    let
      expected = Config $ M.fromList
        [ ("redux", M.fromList [("actions", ""), ("reducers", "")])
        , ("mobx", M.fromList [("state", "")])
        ]
    result <- (Y.decodeFileThrow :: FilePath -> IO Config) "test/test.yaml"
    result `shouldBe` expected
