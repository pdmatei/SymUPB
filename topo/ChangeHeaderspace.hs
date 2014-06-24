module ChangeHeaderspace where
import Flow
import CompactFlow
import Utils
import Data.List

--standardHeader = [CVar "Source-IP", CVar "Dest-IP", CVar "Proto", CVar "Source-TCP", CVar "Dest-TCP", CVar "Options", CVar "TTL"]
headerList = ["Source-IP","Dest-IP","Proto","Source-TCP","Dest-TCP","Options", "TTL"]

