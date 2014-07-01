class SymnetParser:
    
    tab = "    "
    
    def __init__(self, parser):
        self.parser = parser
        
    def find_lines(self, regex):
        return self.parser.find_lines(regex)
        
    def find_children(self, regex):
        original = self.parser.find_children(regex)
        return self.reformat(original)
        
    def find_all_children(self,regex):
        original = self.parser.find_all_children(regex)
        return self.reformat(original)
        
    def find_parents_w_child(self,p_regex,c_regex):
        return self.parser.find_parents_w_child(p_regex,c_regex)
        
    def find_parents_wo_child(self,p_regex,c_regex):
        return self.parser.find_parents_wo_child(p_regex,c_regex)
    
    '''
    The original parser would return the current element as part of it's list of children. To avoid that, we use the reformat function, which eliminates the current element (namely the first) from the list
    '''
    def reformat(self,lst):
        #return lst
        return lst[1:len(lst)]