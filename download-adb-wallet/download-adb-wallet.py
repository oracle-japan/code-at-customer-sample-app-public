import os, random, string, shutil, argparse, oci
from oci.database import DatabaseClient
from oci.database.models import GenerateAutonomousDatabaseWalletDetails

def download_wallet(id, dir, password=None, update_sqlnet_ora=False):
    zip_file = os.path.join(dir, 'wallet.zip')
    os.makedirs(dir, exist_ok=True)

    pw = password if password else 'z0' + ''.join(random.choices(string.ascii_letters + string.digits, k=14))

    signer = oci.auth.signers.InstancePrincipalsSecurityTokenSigner()
    details = GenerateAutonomousDatabaseWalletDetails(password=pw, generate_type='SINGLE')
    db_client = DatabaseClient({}, signer=signer) # type: DatabaseClient
    response_data = db_client.generate_autonomous_database_wallet(id, details).data
    with open(zip_file, 'wb') as f:
        for chunk in response_data.raw.stream(1024 * 1024, decode_content=False):
            f.write(chunk)
    shutil.unpack_archive(zip_file, dir)
    print(f'zip file was unpacked in {dir}')

    if not password:
        password_file = os.path.join(dir, 'password')
        with open(password_file, 'wt') as f:
            f.write(pw)
        print(f'Wallet password is saved in {os.path.abspath(password_file)}')

    if update_sqlnet_ora:
        sqlnet_ora = os.path.join(dir, 'sqlnet.ora')
        with open(sqlnet_ora, 'w') as f:
            f.write(f'WALLET_LOCATION = (SOURCE = (METHOD = file) (METHOD_DATA = (DIRECTORY="{os.path.abspath(dir)}")))\n')
            f.write(f"SSL_SERVER_DN_MATCH=yes\n")
        print(f'sqlnet.ora was modified')

if __name__ == '__main__':
    parser = argparse.ArgumentParser()
    parser.add_argument("--id", required=True)
    parser.add_argument("--dir", required=True)
    parser.add_argument("--password")
    parser.add_argument("--update-sqlnet-ora", action='store_true')
    args = parser.parse_args()

    download_wallet(args.id, args.dir, args.password, args.update_sqlnet_ora)
