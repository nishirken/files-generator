module Config where

import qualified Data.Text as T
import qualified Data.Map as M

type SetName = T.Text
type FileName = T.Text
type FileContent = T.Text
type Sets = M.Map SetName (M.Map FileName FileContent)

data Config = Config
  { _sets :: Sets } deriving (Eq, Show)
