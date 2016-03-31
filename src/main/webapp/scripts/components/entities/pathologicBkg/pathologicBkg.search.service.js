'use strict';

angular.module('consultorioOnlineAppApp')
    .factory('PathologicBkgSearch', function ($resource) {
        return $resource('api/_search/pathologicBkgs/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
