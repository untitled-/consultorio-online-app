'use strict';

angular.module('consultorioOnlineUiApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('gynecoobstetricBkg', {
                parent: 'entity',
                url: '/gynecoobstetricBkgs',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'consultorioOnlineUiApp.gynecoobstetricBkg.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/gynecoobstetricBkg/gynecoobstetricBkgs.html',
                        controller: 'GynecoobstetricBkgController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('gynecoobstetricBkg');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('gynecoobstetricBkg.detail', {
                parent: 'entity',
                url: '/gynecoobstetricBkg/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'consultorioOnlineUiApp.gynecoobstetricBkg.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/gynecoobstetricBkg/gynecoobstetricBkg-detail.html',
                        controller: 'GynecoobstetricBkgDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('gynecoobstetricBkg');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'GynecoobstetricBkg', function($stateParams, GynecoobstetricBkg) {
                        return GynecoobstetricBkg.get({id : $stateParams.id});
                    }]
                }
            })
            .state('gynecoobstetricBkg.new', {
                parent: 'gynecoobstetricBkg',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/gynecoobstetricBkg/gynecoobstetricBkg-dialog.html',
                        controller: 'GynecoobstetricBkgDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    menarche: null,
                                    begginingSexualLife: null,
                                    pregnanciesNumber: null,
                                    miscarriagesNumber: null,
                                    cSectionsNumber: null,
                                    pregnancyDetails: null,
                                    latestPapTest: null,
                                    latestPapTestDetails: null,
                                    latestMammography: null,
                                    latestMammographyDetails: null,
                                    usesContraceptives: null,
                                    hasMenopause: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('gynecoobstetricBkg', null, { reload: true });
                    }, function() {
                        $state.go('gynecoobstetricBkg');
                    })
                }]
            })
            .state('gynecoobstetricBkg.edit', {
                parent: 'gynecoobstetricBkg',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/gynecoobstetricBkg/gynecoobstetricBkg-dialog.html',
                        controller: 'GynecoobstetricBkgDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['GynecoobstetricBkg', function(GynecoobstetricBkg) {
                                return GynecoobstetricBkg.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('gynecoobstetricBkg', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('gynecoobstetricBkg.delete', {
                parent: 'gynecoobstetricBkg',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/gynecoobstetricBkg/gynecoobstetricBkg-delete-dialog.html',
                        controller: 'GynecoobstetricBkgDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['GynecoobstetricBkg', function(GynecoobstetricBkg) {
                                return GynecoobstetricBkg.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('gynecoobstetricBkg', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
