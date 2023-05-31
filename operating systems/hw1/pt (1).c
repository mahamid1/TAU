#include <stdlib.h>
#include <assert.h>
#include <stdio.h>
#include "os.h"
/*
 *  we have a 45 bit for VPN
 *  4KB for page size -----> 512 64bit page table entries ------>
 *  512 children in 9 bits per child ------> we need 5-level page table
 *  using (X) & 0x1ff to get the first 9 bits
 * */
void page_table_update(uint64_t pt, uint64_t vpn, uint64_t ppn){
    int entry;
    int i;
    int offset;
    uint64_t* current=phys_to_virt(pt<<12); /* the current level in the page table */
    for(i=0;i<4;i++){
        offset=45-(i*9);
        entry=(vpn>>offset)& 0x1ff;
        if((current[entry] & 0x1 )==0){
            if(ppn==NO_MAPPING){
                return;
            }
            current[entry]=(alloc_page_frame()<<12)+1;/* +1 for the valid bit */
        }
        current= phys_to_virt(current[entry]-1); /*pointer to the next level*/
    }
    /* updating ppn in the 5th level */
    entry= (vpn) & 0x1ff;
    if(ppn==NO_MAPPING){
        current[entry]=0;
    }
    else{
        current[entry]=(ppn<<12)+1;
    }
    return;
}


uint64_t page_table_query(uint64_t pt,uint64_t vpn){
    int i ;
    int offset;
    int entry;
    uint64_t* current=phys_to_virt(pt<<12);
    for(i=0;i<5;i++){
        offset=45-(i*9);
        entry=(vpn>>offset)& 0x1ff;
        if((current[entry] & 0x1 )==0)
        {
            return NO_MAPPING;
        }
        current= phys_to_virt((current[entry])-1); /*pointer to the next level*/
    }

    entry=(vpn)& 0x1ff;
    if ((current[entry] & 0x1) ==0){
        return NO_MAPPING;
    }
    return current[entry]>>12;
}