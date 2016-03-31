'use strict';

angular.module('consultorioOnlineAppApp')
    .factory('NonPathologicBkgSearch', function ($resource) {
        return $resource('api/_search/nonPathologicBkgs/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
