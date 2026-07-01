import re, glob, json

data = json.load(open('postman/Insurance Policy & Claim Management System (UPDATED).postman_collection.json', encoding='utf-8'))
pm_endpoints = []
def extract(items):
    for item in items:
        if 'item' in item: extract(item['item'])
        elif 'request' in item:
            req = item['request']
            method = req['method']
            url = req['url']['raw'] if isinstance(req['url'], dict) else req['url']
            url = url.replace('{{baseUrl}}', '').split('?')[0] # remove base URL and query params
            pm_endpoints.append(f'{method} {url}')
extract(data.get('item', []))
pm_endpoints = set(pm_endpoints)

src_endpoints = []
for file in glob.glob('src/main/java/com/insurance/demo/controller/*.java'):
    with open(file, 'r', encoding='utf-8') as f:
        content = f.read()
        base_path = ''
        base_match = re.search(r'@RequestMapping\(\"(.*?)\"\)', content)
        if base_match:
            base_path = base_match.group(1)
        
        methods = re.findall(r'@(Get|Post|Put|Patch|Delete)Mapping(?:\(\"(.*?)\".*?\)|(?:\((.*?)\))|\s*\n)', content)
        for m in methods:
            method_type = m[0].upper()
            sub_path = ''
            if m[1]:
                sub_path = m[1]
            elif m[2]:
                val = re.search(r'value\s*=\s*\"(.*?)\"', m[2])
                if val: sub_path = val.group(1)
                else: 
                    val = re.search(r'\"(.*?)\"', m[2])
                    if val: sub_path = val.group(1)
            
            full_path = base_path + sub_path
            
            # Normalize path variables {id} -> {var}
            full_path = re.sub(r'\{.*?\}', '{var}', full_path)
            src_endpoints.append(f'{method_type} {full_path}')

src_endpoints = set(src_endpoints)
pm_norm = set([re.sub(r'\{.*?\}', '{var}', e) for e in pm_endpoints])

print('--- MISSING IN POSTMAN ---')
for e in sorted(list(src_endpoints - pm_norm)):
    print(e)
    
print('\n--- POTENTIALLY INCORRECT IN POSTMAN ---')
for e in sorted(list(pm_norm - src_endpoints)):
    print(e)
