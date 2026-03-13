import { Injectable } from '@angular/core';

@Injectable()
export class ConfigService {

    constructor() { }

    private readonly _api_url = '/gedbrowserng/v1';

    private readonly _refresh_token_url = this._api_url + '/refresh';

    private readonly _login_url = this._api_url + '/login';

    private readonly _logout_url = this._api_url + '/logout';

    private readonly _change_password_url = this._api_url + '/changePassword';

    private readonly _whoami_url = this._api_url + '/whoami';

    private readonly _user_url = this._api_url + '/user';

    private readonly _users_url = this._user_url + '/all';

    private readonly _reset_credentials_url = this._user_url + '/reset-credentials';

    private readonly _foo_url = this._api_url + '/foo';

    private readonly _map_key_url = this._api_url + '/map-key';

    private readonly _signup_url = this._api_url + '/signup';

    get reset_credentials_url(): string {
        return this._reset_credentials_url;
    }

    get refresh_token_url(): string {
        return this._refresh_token_url;
    }

    get whoami_url(): string {
        return this._whoami_url;
    }

    get users_url(): string {
        return this._users_url;
    }

    get login_url(): string {
        return this._login_url;
    }

    get logout_url(): string {
        return this._logout_url;
    }

    get change_password_url(): string {
        return this._change_password_url;
    }

    get foo_url(): string {
        return this._foo_url;
    }

    get map_key_url(): string {
        return this._map_key_url;
    }

    get signup_url(): string {
        return this._signup_url;
    }
}
